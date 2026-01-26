from sqlalchemy.orm import Mapped, mapped_column
from sqlalchemy import String, DateTime
from datetime import datetime, timezone
import uuid
from app.models.base import Base

class Device(Base):
    __tablename__ = "devices"

    id: Mapped[str] = mapped_column(String(80), primary_key=True, default=lambda: f"device-{uuid.uuid4()}")
    user: Mapped[str] = mapped_column(String(80), nullable=False)
    device_token: Mapped[str] = mapped_column(String(300), unique=True, nullable=False)
    created_at: Mapped[datetime] = mapped_column(DateTime, default=lambda: datetime.now(timezone.utc), nullable=False)
