FROM node:18

WORKDIR /app

COPY package.json package-lock.json ./


RUN npm install

COPY . .

EXPOSE 4200

# CMD ["npm", "run", "start"]

CMD ["npx", "ng", "serve", "--host", "0.0.0.0"]
