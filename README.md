# Yelp Fusion App

This is a simple yelp business finder for Irvine CA. Yelp Fusion App simply gives an insight on local businesses and easily links the the Yelp App for further insight.

# Architecture

App uses MVVM acrhitecture with redux and Rx. The simple flow of data allows for clean coding and easy testing. Using Retrofit and okhttp for network connections and dagger2 for dependency injection, this app is easily scaled and built upon.

# gen.kts

Using kotlin scripts, there is a easy way to knock boiler plate code out of the way. Generate activities, fragments, use cases with one simple command.

Usage `./gen.kts` {activity/fragment} {path under presentation}

# obfuscate.kts

A simple way to keep keys private. Simple obfuscation and deobfuscation of keys adds a easy layer of security to this app.

Usage `./obfuscate.kts {key}` -> output = `OBF = ......`. Inside the app, use the extension function `OBF = ......`.deobfuscate() to get the original value.
