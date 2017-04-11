 
.:: SpreaD3-web-ui ::.
==============

#### Purpose
User interface for Spread3-web.

## Dev

### Run application:

start the backend:

```
lein run
```

start the frontend:

```
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:8080](http://localhost:8080).

### Run tests:

backend tests:

```
lein test
```

frontend tests:

```
lein doo chrome test
```
or

```
lein doo node test
```

It will autobuild and rerun the tests as the watched files change.
More info: [https://github.com/bensu/doo](doo).

## Dist

```
lein deploy
```

