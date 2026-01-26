from app.core.config import settings

def send_push(device_tokens: list[str], title: str, body: str, data: dict) -> None:
    if not settings.fcm_server_key:
        return
    return
