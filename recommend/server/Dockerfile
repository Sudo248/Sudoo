FROM python:3.9-slim-buster

WORKDIR /app

COPY requirements.txt requirements.txt
RUN python -m pip install --no-cache-dir -r requirements.txt

COPY services services
COPY app.py app.py

CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0"]