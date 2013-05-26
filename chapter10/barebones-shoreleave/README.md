# Barebones Shoreleave.

[Shoreleave][1] is billed as

> A smarter client-side in ClojureScript
>
> Shoreleave is a collection of integrated libraries that focuses on:
>
> * Security
> * Idiomatic interfaces
> * Common client-side strategies
> * HTML5 capabilities
> * ClojureScript's advantages
>
> It builds upon efforts found in other ClojureScript projects, such as Fetch and ClojureScript:One.

More concisely (and reductively), Shoreleave is a set of web-app libraries that make it simpler to get a ClojureScript-based client-side connected to a Ring/Compojure-based Clojure backend.

This document represents my attempt to figure out how Shoreleave works, what all the different libraries do (although I only end up using a few of them), and what the bare minimum necessary is for getting a Shoreleave/ClojureScript + Ring app set up.  It assumes you are relatively familiar with Ring/Compojure and getting Clojure web apps running with lein ring/in the repl.

## Libraries

The libraries that are listed on the main [Shoreleave github project][1] page are as follows:

* [shoreleave-services][2] - Shoreleave's API for third-party remote services
* [shoreleave-pubsub][3] - Shoreleave's publish/subscribe system
* [shoreleave-worker][4] - Shoreleave's embedded web worker utilities
* [shoreleave-remote-ring][5] - Ring- (& Compojure-) friendly remote functions for use w/ shoreleave + ClojureScript
* [shoreleave-remote][6] - Shoreleave's rpc/xhr/jsonp facilities
* [shoreleave-browser][7] - Shoreleave's enhanced browser utilities
* [shoreleave-core][8] - Shoreleave's core auxiliary functions
* [shoreleave-remote-noir][9] - Shoreleave's noir hookups for secure http-rpc

Presumably [shoreleave-remote-noir][9] was meant to replace Noir's "fetch" lib, and appears to be superseded by [shoreleave-remote-ring][5].  [shoreleave-browser][7] is a set of ClojureScript utilities for manipulating cookies, HTML5's blobs and history functionality, which I won't touch in this tutorial.  Similarly, [shoreleave-pubsub][3], [shoreleave-worker][4] and [shoreleave-core][8] (which, unless I'm missing something--quite possible--I find to be confusingly named, as it seems to deal exclusively with clojure.browser.repl helper functionality) represent use-cases outside of the scope of this tutorial, which is really about getting the most basic, unsophisticated (in contrast with what shoreleave-pubsub represents) implementation of Shoreleave up and running.

In addition to the libraries above, there are two apps which illustrate sample usage of Shoreleave:

* [shoreleave-baseline][12] - A baseline application to get started with Compojure+Shoreleave
* [demo-shoreleave-solr][13] - A demo app using Shoreleave, SOLR, and Noir (note: did not investigate this one at all, have no idea how current it is.)

I learned a lot from [Shoreleave-baseline][12] and reference it in this tutorial, but (no offense to the creator) I feel that it doesn't represent the best "basic" app for understanding what Shoreleave is composed of and how to integrate it with a Ring/Compojure web application.

Please also note that this tutorial does not illustrate every step to getting this app up and running: it is assumed you will refer to the app in the [git repo][16] for a functioning version.  I use other Clojure/ClojureScript libraries like [Enlive][18] and [jayq][19] which I don't go into in this tutorial, so please refer to that code and the related documentation for those projects for details.

## Including the Shoreleave libraries in your project.

There exists a wrapper lib for some of these, simply called "shoreleave."  It can be included in your project.clj file using the following:

```clojure
[shoreleave "0.3.0"]
```

and it includes the following libraries (see [clojars][10]):

* [shoreleave-core][8]
* [shoreleave-browser][7]
* [shoreleave-worker][4]
* [shoreleave-pubsub][3]
* [shoreleave-remote][6]
* [shoreleave-services][2]

However, it doesn't include shoreleave-remote-ring, which can be a problem, as it contains libraries necessary for a "basic shoreleave app" (as I'm defining it).  You'll [need this][14] in your project.clj:

```clojure
[shoreleave/shoreleave-remote-ring "0.3.0"]
```

...although [this is not obvious from the documentation][11].

It turns out, you really only need shoreleave-remote and shoreleave-remote-ring to get a basic app up and running.  I have just these two in my project.clj and for what I'm doing it works fine:

```clojure
[shoreleave/shoreleave-remote "0.3.0"]
[shoreleave/shoreleave-remote-ring "0.3.0"]
```

## Connecting things together

The most basic--yet still somewhat "real-world"--example of AJAX interaction I could think of when starting this was:

* On the client-side, click on a link.
* Behind the scenes, the client code initiates a connection with the server.
* The server gets it and responds.
* We then display evidence that the server has responded on the client-side.

The only thing I didn't like about how Shoreleave-baseline did it was, it initiated the act of connecting with the server based on loading a JS file.  I wanted my example to be slightly more representative of a "real-world" AJAX interaction with the server, even if still quite simplistic.

### Server-side

To start, I created a really basic compojure app

```bash
$ lein new compojure hello-world
```

...and I pulled the bits out of Shoreleave-baseline that I wanted.  For the handler, I added the necessary libraries:

```clojure
(ns barebones-shoreleave.handler
  ;; ...
  (:require
   [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]
  ;; ...
```

In Shoreleave, a lot of interaction is abstracted out.  shoreleave-remote-ring provides you with a `defremote` call to allow you to define something like a Compojure route which your client-side code can then access in a RPC-like fashion.  I added a very simple `defremote`:

```clojure
(defremote ping [pingback]
  (str "You have hit the API with: " pingback))
```

And finally, to get this actually integrated with the app, I added the `wrap-rpc` function to the handler code:

```clojure
(def app
  (-> app-routes
      wrap-rpc
      handler/site))
```

The rest was just scaffolding to get some basic templating in place:

```clojure
;; Enlive template
(html/deftemplate main-layout
  "public/templates/index.html"
  [text]
  [:div#content] (html/html-content text))

;; ...

;; Barely modified default routes
(defroutes app-routes
  (GET "/" []
       (main-layout "<a href='#' id='click'>Click me!</a>"))
  (route/resources "/")
  (route/not-found "Not Found"))
```

That was it for the server-side.

### Client-side

In my `src/barebones_shoreleave` dir I then added a directory called `client` per the convention in Shoreleave-baseline, and mimicking Shoreleave-baseline added a `main.cljs` file.  This simply contained the flip side of the server-side code, a call to the remote I had defined in the handler:

```clojure
(ns barebones-shoreleave.main
  (:use
   [jayq.core :only [$ bind]])
  (:require
   [shoreleave.remote])
  (:require-macros
   ;; https://github.com/shoreleave/shoreleave-remote
   [shoreleave.remotes.macros :as srm]))

(def $click ($ :a#click))

(bind
 $click "click"
 (fn []
   (srm/rpc
    (ping "Testing...") [pong-response]
    (js/alert pong-response))))
```

It uses jayq to get some jQuery event listener functionality, and binds the anchor link's click to the Shoreleave remote call.

That's it!  It is very simple, it works and I think it is easy to understand.  It doesn't begin to address the full power of Shoreleave, but we'll leave that for the next tutorial...

## Protection against CSRF

But it's not secure!  At the bare minimum, I want to know how to provide simple CSRF-protection on all my AJAX POST requests.

As the docs for Shoreleave-baseline state,

> Shoreleave-baseline is already wired up to do a host of best-practices for web applications. This includes, but is not limited to client-side caching, compression, and CSRF protection (all via Ring Middleware).

"Ring Middleware" in this case means, specifically, [ring-anti-forgery][15].  So, plugging it into our app we get:

```clojure
(def app
  (-> app-routes
      wrap-rpc
      ring.middleware.anti-forgery/wrap-anti-forgery
      (handler/site)))
```

Sweet, it was easy!  Let's reload and...huh?

```
POST http://127.0.0.1:3000/_shoreleave 403 (Forbidden) baseline.js:16916
XHR ERROR: <h1>Invalid anti-forgery token</h1> 
```

...is what I see in my console.  Hmm, this is weird.  At least we know CSRF protection is working I suppose...

I dug back into Shoreleave-baseline (and after an hour of digging through the code), I could see that there was a cookie getting set that I don't have in my own simple app:

```
__anti-forgery-token iaED%2B9pTukUkCo6bmllUVQXlnvp1x82Qe%2BLITGran9uvoCo1qUSjGl7mkJ2WxZ%2FZQlqZI%2BK89COJdbpz
```

Poking around Shoreleave-baseline's `baseline` directory code (i.e. the code in the repo I had assumed represents the Shoreleave app itself), I see nothing obvious that is adding this cookie to the response.  Could it be that the ring-anti-forgery packaged in the repo is modified?

```clojure
;;...
;; The token should also be in a cookie for JS (proper double submit)
(assoc-in [:cookies "__anti-forgery-token"] token)))))
```

[Sneaky!][17]  Rather than copy this over, let's add our own simple middleware wrapper to pull the token from the session and set it as a cookie:

```clojure
;; src/barebones_shoreleave/middleware.clj
(defn wrap-add-anti-forgery-cookie
  "Mimics code in Shoreleave-baseline's
   customized ring-anti-forgery middleware."
  [handler & [opts]]
  (fn [request]
    (let [response (handler request)]
      (if-let [token (-> request :session (get "__anti-forgery-token"))]
        (assoc-in response [:cookies "__anti-forgery-token"] token)
        response))))
```

(Please note, [there is a proposal][20] to add this functionality to ring-anti-forgery, but it's not in there yet.)

And there we have it, a very simple Shoreleave app with basic CSRF protection baked-in.

Comments/criticisms/pull requests welcome! (You can attach "ddellacosta" to the domain used by Google's mail service to contact me by email).

## License

Distributed under the MIT License (http://dd.mit-license.org/)
Text copyright © 2013 Dave Della Costa

[1]: https://github.com/shoreleave
[2]: https://github.com/shoreleave/shoreleave-services
[3]: https://github.com/shoreleave/shoreleave-pubsub
[4]: https://github.com/shoreleave/shoreleave-worker
[5]: https://github.com/shoreleave/shoreleave-remote-ring
[6]: https://github.com/shoreleave/shoreleave-remote
[7]: https://github.com/shoreleave/shoreleave-browser
[8]: https://github.com/shoreleave/shoreleave-core
[9]: https://github.com/shoreleave/shoreleave-remote-noir
[10]: https://clojars.org/shoreleave
[11]: https://github.com/shoreleave/shoreleave-remote-ring/issues/5
[12]: https://github.com/shoreleave/shoreleave-baseline
[13]: https://github.com/shoreleave/demo-shoreleave-solr
[14]: https://clojars.org/shoreleave/shoreleave-remote-ring
[15]: https://github.com/weavejester/ring-anti-forgery
[16]: https://github.com/ddellacosta/barebones-shoreleave
[17]: https://github.com/shoreleave/shoreleave-baseline/blob/master/src/ring/middleware/anti_forgery.clj#L22
[18]: https://github.com/cgrand/enlive
[19]: https://github.com/ibdknox/jayq
[20]: https://github.com/weavejester/ring-anti-forgery/pull/13
