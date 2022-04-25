#!/bin/bash

filebeat setup
service filebeat start
nginx -g 'daemon off;'