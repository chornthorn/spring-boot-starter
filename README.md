

## Generate RSA key pair
```bash
openssl genpkey -algorithm RSA -out src/main/resources/certs/private.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -in src/main/resources/certs/private.pem -pubout -out src/main/resources/certs/public.pem
```