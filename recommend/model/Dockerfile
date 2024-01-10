FROM tensorflow/tensorflow:2.15.0

WORKDIR /app
COPY requirements.txt requirements.txt
RUN python -m pip install --no-cache-dir -r requirements.txt

COPY service-account.json /app/service-account.json
COPY sudoo_neural_collaborative_filtering.py /app/build_model_script.py

CMD [ "python3", "build_model_script.py" ]