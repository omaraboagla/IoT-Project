FROM node:18 AS build

WORKDIR /app

COPY package.json package-lock.json ./


RUN npm install

COPY . .

EXPOSE 4200

CMD ["npx", "ng", "serve", "--host", "0.0.0.0"]
