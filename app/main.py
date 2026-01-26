from pathlib import Path
from dotenv import load_dotenv

# Ensure .env is loaded regardless of the process CWD.
ENV_PATH = Path(__file__).resolve().parents[1] / ".env"
load_dotenv(dotenv_path=ENV_PATH)

from fastapi import FastAPI
from app.core.config import settings
from app.db.init_db import init_db
from app.db.session import SessionLocal
from app.utils.seed_data import seed_alerts
from app.api.routes import router

app = FastAPI(title=settings.app_name)

@app.on_event("startup")
def on_startup():
    init_db()
    db = SessionLocal()
    try:
        seed_alerts(db)
    finally:
        db.close()

app.include_router(router)

@app.get("/health")
def health():
    return {"status": "ok"}
