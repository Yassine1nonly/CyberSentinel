from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy.orm import Session
from datetime import datetime, timezone
from pydantic import BaseModel
from app.db.session import get_db
from app.core.security import require_api_key
from app.models.alert import Alert
from app.models.device import Device
from app.services.fcm import send_push

router = APIRouter(prefix="/api", dependencies=[Depends(require_api_key)])

class AckBody(BaseModel):
    ack_by: str
    comment: str | None = None

class RegisterDeviceBody(BaseModel):
    device_token: str
    user: str

class MockAlertBody(BaseModel):
    title: str
    source: str
    severity: str
    description: str

@router.get("/alerts")
def list_alerts(
    severity: str | None = Query(default=None),
    ack: bool | None = Query(default=None),
    db: Session = Depends(get_db),
):
    q = db.query(Alert)
    if severity:
        q = q.filter(Alert.severity == severity)
    if ack is True:
        q = q.filter(Alert.ack_by.isnot(None))
    if ack is False:
        q = q.filter(Alert.ack_by.is_(None))
    return q.order_by(Alert.timestamp.desc()).all()

@router.get("/alerts/{alert_id}")
def get_alert(alert_id: str, db: Session = Depends(get_db)):
    alert = db.query(Alert).filter(Alert.id == alert_id).first()
    if not alert:
        raise HTTPException(status_code=404, detail="Alert not found")
    return alert

@router.post("/alerts/{alert_id}/ack")
def ack_alert(alert_id: str, body: AckBody, db: Session = Depends(get_db)):
    alert = db.query(Alert).filter(Alert.id == alert_id).first()
    if not alert:
        raise HTTPException(status_code=404, detail="Alert not found")

    alert.ack_by = body.ack_by
    alert.ack_comment = body.comment
    alert.ack_at = datetime.now(timezone.utc)
    alert.status = "acknowledged"
    db.commit()
    db.refresh(alert)
    return alert

@router.post("/register-device")
def register_device(body: RegisterDeviceBody, db: Session = Depends(get_db)):
    existing = db.query(Device).filter(Device.device_token == body.device_token).first()
    if existing:
        existing.user = body.user
        db.commit()
        return {"status": "updated"}

    d = Device(user=body.user, device_token=body.device_token)
    db.add(d)
    db.commit()
    return {"status": "registered"}

@router.post("/mock-alerts")
def create_mock_alert(body: MockAlertBody, db: Session = Depends(get_db)):
    alert = Alert(
        title=body.title,
        source=body.source,
        severity=body.severity,
        description=body.description,
        evidence=[],
        status="open",
    )
    db.add(alert)
    db.commit()
    db.refresh(alert)

    tokens = [d.device_token for d in db.query(Device).all()]
    if body.severity == "critical":
        send_push(tokens, f"CRITICAL: {body.title}", body.description, {"alert_id": alert.id, "severity": body.severity})

    return alert
