# BoringCMS

It's a content management system having a simplistic design and web logic.

# Installation

Just build and run with Docker.

```shell
docker compose up --build
```

You can build with your local Maven files, and then run.

```shell
docker build --build-context m2="$HOME/.m2" -t stagyrite/boringcms:latest .
docker compose up
```

# Introduction to XSLT
XSLT transformations are a well-known basis of numerous servlets
around the Internet, as well as in the corporate intranets.
The key concept of programming with XSLT matches the foundations
of servlets and JSP. It's easy to understand how to create
an XSLT transformation knowing servlets, JSP, and even more
web technologies.
