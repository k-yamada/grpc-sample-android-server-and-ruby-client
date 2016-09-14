# [WIP] grpc-sample-android-server-and-ruby-client
gRPC sample that implement android server and ruby client

# Setup android server


# Setup ruby client

```
cd ruby
bundle install

# run greeter_client
# ex: ruby greeter_client.rb <SERIAL_NO> <MESSAGE>
ruby greeter_client.rb 01ABD00F00E hello
```

# debug

```
apk_path=$(adb shell pm path io.grpc.helloworldexample | tr -d '\\r' | awk -F: '{print $2}')
adb shell export CLASSPATH=$apk_path\; exec app_process /system/bin io.grpc.helloworldexample.HelloWorldServer
adb forward tcp:50051 tcp:50051
```
