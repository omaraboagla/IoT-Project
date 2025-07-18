# Use Node.js base image
FROM node:18

# Set working directory
WORKDIR /app

# Copy dependency definitions
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the full app source
COPY . .

# Expose Angular's default dev port
EXPOSE 4200

# Start Angular CLI dev server, allow all hosts
CMD ["npx", "ng", "serve", "--host", "0.0.0.0", "--disable-host-check"]
