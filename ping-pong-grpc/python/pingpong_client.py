import grpc
import pingpong_pb2
import pingpong_pb2_grpc

def run():
  channel = grpc.insecure_channel('localhost:8080')

  stub = pingpong_pb2_grpc.PingPongServiceStub(channel)

  request = pingpong_pb2.PingRequest(message='Ping')

  print("Ping Pong client calling server...")

  response = stub.ping(request)

  print("Response received, message: " + response.message)

if __name__ == '__main__':
  run()