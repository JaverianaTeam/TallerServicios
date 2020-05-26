#!/bin/bash
docker build . -t rocampoa/usuarios:1.0
#docker run -d -p 8186:8186 --name usuarios-01 --net javeiana-aes-modval-net javeriana-aes-modval/demo-time:1.0
docker push rocampoa/usuarios:1.0