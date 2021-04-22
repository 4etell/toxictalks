export function goToLogin() {
    if (!(this.$route.path === '/login')) {
        this.$router.push('/login')
    }
}

export function goToRegistration() {
    if (!(this.$route.path === '/registration')) {
        this.$router.push('/registration')
    }
}

export function goToHome() {
    if (!(this.$route.path === '/')) {
        this.$router.push('/')
    }
}

export function goToProfile() {
    if (!(this.$route.path === '/profile')) {
        this.$router.push('/profile')
    }
}

export function goToChats() {
    if (!(this.$route.path === '/chats')) {
        this.$router.push('/chats')
    }
}

export function goToTopics() {
    if (!(this.$route.path === '/topics')) {
        this.$router.push('/topics')
    }
}