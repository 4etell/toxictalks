import SockJS from 'sockjs-client'
import {Stomp} from '@stomp/stompjs'


let stompClient = null
const handlers = []

export function connectTopicWs() {
    const socket = new SockJS('/topics-websocket')
    stompClient = Stomp.over(function () {
        return new SockJS('/topics-websocket')
    });
    stompClient.debug = () => {}
    stompClient.connect({}, frame => {
        stompClient.subscribe('/topic/chattopics', message => {
            handlers.forEach(handler => handler(JSON.parse(message.body)))
        })
    })
}

function f() {

}

export function addHandlerTopicWs(handler) {
    handlers.push(handler)
}

export function disconnectTopicWs() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
}