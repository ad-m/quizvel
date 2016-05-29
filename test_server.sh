#!/bin/bash
set -e
curl 'localhost:2000/user' --http1.0 -v -X POST -d '{"username":"xyz","password":"xyz"}' -v;
echo;
curl 'localhost:2000/user' --http1.0 -v;
echo;
curl 'xyz:xyz@localhost:2000/user/~promote' --http1.0 -X POST -v -d '{"secret":"Hero"}';
echo;
curl 'localhost:2000/user' --http1.0 -v;
echo;
curl 'xyz:xyz@localhost:2000/~save' --http1.0 -v;
