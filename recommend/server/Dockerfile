FROM tensorflow/tensorflow:2.15.0

WORKDIR /app

RUN python -m pip install --ignore-installed blinker
COPY requirements.txt requirements.txt
RUN python -m pip install --no-cache-dir -r requirements.txt

COPY services services
COPY app.py app.py

CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0"]