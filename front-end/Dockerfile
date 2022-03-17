FROM node:14.15.1

WORKDIR /app

COPY . /app/

RUN npm install --verbose

RUN npm run build

RUN npm install -g serve --loglevel verbose

CMD ["serve", "-l", "5000" ,"build"]