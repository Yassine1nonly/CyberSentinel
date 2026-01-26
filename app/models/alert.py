from sqlalchemy.orm import Mapped, mapped_column
from sqlalchemy import String, DateTime, Text, JSON
from datetime import datetime, timezone
import uuid
from app.models.base import Base

class Alert(Base):
    __tablename__ = "alerts"

    id: Mapped[str] = mapped_column(String(80), primary_key=True, default=lambda: f"alert-{uuid.uuid4()}")
    title: Mapped[str] = mapped_column(String(200), nullable=False)
    source: Mapped[str] = mapped_column(String(120), nullable=False)
    severity: Mapped[str] = mapped_column(String(20), nullable=False)
    timestamp: Mapped[datetime] = mapped_column(DateTime, default=lambda: datetime.now(timezone.utc), nullable=False)
    description: Mapped[str] = mapped_column(Text, nullable=False)
    evidence: Mapped[list] = mapped_column(JSON, default=list, nullable=False)
    status: Mapped[str] = mapped_column(String(30), default="open", nullable=False)
    ack_by: Mapped[str | None] = mapped_column(String(80), nullable=True)
    ack_comment: Mapped[str | None] = mapped_column(String(400), nullable=True)
    ack_at: Mapped[datetime | None] = mapped_column(DateTime, nullable=True)
