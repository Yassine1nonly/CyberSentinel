from pydantic import Field
from pydantic_settings import BaseSettings, SettingsConfigDict

class Settings(BaseSettings):
    model_config = SettingsConfigDict(env_file=".env", extra="ignore", case_sensitive=False)
    app_name: str = "CyberSentinel API"
    api_key: str = Field(default="CHANGE_ME", alias="API_KEY")
    db_url: str = Field(default="sqlite:///./cybersentinel.db", alias="DATABASE_URL")
    fcm_server_key: str | None = Field(default=None, alias="FCM_SERVER_KEY")

settings = Settings()
