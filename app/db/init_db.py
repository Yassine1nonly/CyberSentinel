from app.db.session import engine
from app.models.base import Base
from app.models.alert import Alert
from app.models.device import Device

def init_db() -> None:
    Base.metadata.create_all(bind=engine)
