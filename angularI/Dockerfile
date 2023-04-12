# Estagio 1 - Responsável por gerar o build da nossa aplicação
FROM node:18.13.0 as node
WORKDIR /app
COPY package.json /app/
RUN npm i npm@latest -g
RUN npm install
COPY . .
RUN npm run build
# Estagio 2 - Responsável por expor nossa aplicação
FROM nginx:alpine
COPY --from=node /app/dist/angular-treino /usr/share/nginx/html
COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf
