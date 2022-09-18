FROM node:14.15.1

WORKDIR /app

COPY . /app/

RUN npm install

RUN npm run build

RUN npm install -g serve

CMD ["serve", "-l", "5000" ,"build"]