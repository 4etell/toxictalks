import SockJS from 'sockjs-client'
import {Stomp} from '@stomp/stompjs'


let stompClient = null
const handlers = []

export function connectChatWs(userId) {
    const socket = new SockJS('/messages-websocket')
    stompClient = Stomp.over(function () {
        return new SockJS('/messages-websocket')
    });
    stompClient.debug = () => {}
    stompClient.connect({}, frame => {
        stompClient.subscribe('/topic/chat/' + userId, message => {
            handlers.forEach(handler => handler(JSON.parse(message.body)))
        })
    })
}

export function addHandlerChatWs(handler) {
    handlers.push(handler)
}

export function disconnectChatWs() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
}