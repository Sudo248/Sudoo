FROM tensorflow/tensorflow:2.15.0

WORKDIR /app
COPY requirement.txt requirement.txt
RUN python -m pip install --no-cache-dir -r requirement.txt

COPY sudoo_neural_collaborative_filtering.py /app/build_model_script.py

CMD [ "python3", "build_model_script.py" ]