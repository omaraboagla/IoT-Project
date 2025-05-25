FROM jenkins/jenkins:lts

USER root

# Install dependencies including Docker CLI, git, maven, curl
RUN apt-get update && \
    apt-get install -y \
        apt-transport-https \
        ca-certificates \
        curl \
        gnupg \
        lsb-release \
        maven \
        git && \
    # Add Docker’s official GPG key and repo
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    echo "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list && \
    apt-get update && \
    apt-get install -y docker-ce-cli && \
    rm -rf /var/lib/apt/lists/*

# Install Docker Compose (latest version)
RUN curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose

# Verify installation (optional)
RUN docker-compose --version

# Add Jenkins user to docker group if exists
RUN if getent group docker; then \
        usermod -aG docker jenkins; \
    else \
        echo "docker group not found, skipping usermod"; \
    fi

USER jenkins
