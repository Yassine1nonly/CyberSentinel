from sqlalchemy.orm import Session
from datetime import datetime, timezone, timedelta
from app.models.alert import Alert

def seed_alerts(db: Session) -> None:
    if db.query(Alert).count() > 0:
        return

    now = datetime.now(timezone.utc)
    sample = [
        Alert(
            title="Multiple failed logins 5 attempts",
            source="auth-server-1",
            severity="critical",
            timestamp=now - timedelta(minutes=20),
            description="Multiple failed login attempts from IP 1.2.3.4",
            evidence=[{"type": "log", "url": "/evidence/demo/logs1.txt"}],
        ),
        Alert(
            title="Suspicious IP scanning detected",
            source="fw-edge-1",
            severity="high",
            timestamp=now - timedelta(hours=2),
            description="Port scan pattern detected from IP 5.6.7.8",
            evidence=[{"type": "log", "url": "/evidence/demo/logs2.txt"}],
        ),
        Alert(
            title="Malware signature match",
            source="edr-agent-3",
            severity="critical",
            timestamp=now - timedelta(hours=6),
            description="Possible malware detected on endpoint win10-laptop-22",
            evidence=[{"type": "screenshot", "url": "/evidence/demo/shot1.png"}],
        ),
        Alert(
            title="New admin user created",
            source="ad-controller",
            severity="low",
            timestamp=now - timedelta(days=1),
            description="Administrative account created in directory service",
            evidence=[],
        ),
    ]

    db.add_all(sample)
    db.commit()
