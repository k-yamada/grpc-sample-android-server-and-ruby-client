# grpc-sample-android-server-and-ruby-client

It's sample project to run gRPC server on Android.

# Setup android (gRPC-server)

## build

```
$ cd android/helloworld/
$ ./gradlew build
```

## Install

Open `android/helloworld` project by Android Studio, and Run app.

# Setup ruby (gRPC-client)

```
$ cd ruby
$ bundle install

# run greeter_client. ex) ruby greeter_client.rb <ANDROID_SERIAL_NO> <MESSAGE>
$ ruby greeter_client.rb XXXXXXXX world
"Greeting: Hello world" # <- Success!
```

----

## Run HelloWorldServer by shell

```
apk_path=$(adb shell pm path io.grpc.helloworldexample | tr -d '\r' | cut -d: -f 2)
adb shell export CLASSPATH=$apk_path\; exec app_process /system/bin io.grpc.helloworldexample.HelloWorldServer
adb forward tcp:50051 tcp:50051
```
