from google.cloud import compute_v1

def stopGooleComputeEngine():
  # Create a client
  client = compute_v1.InstancesClient()

  # Initialize request argument(s)
  request = compute_v1.StopInstanceRequest(
      instance="sudoo-worker-4",
      project="sudoo-404614",
      zone="asia-southeast1-b",
  )

  # Make the request
  response = client.stop(request=request)

  # Handle the response
  print(response)

stopGooleComputeEngine()