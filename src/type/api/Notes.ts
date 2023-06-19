export interface Notes {
    token: string
    content: string
    name: string
    title?:string
}

export interface SaveReq {
    userId: number | undefined
    notes: Array<Notes> | undefined
}

export interface NotifyReq {
    userId: number
    title: string
    content: string
    method: number
    notifyTriggerTime:string
}
