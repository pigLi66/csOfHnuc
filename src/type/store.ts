export type WidgetCacheMap = {
  [key: string]: { left: number; top: number; status?: any }
} & {
  set(key: string, content?: { left?: number; top?: number; status?: any }): void
  del(key: string): void
}

export interface CustomWidgetCacheMap {
  [key: string]: {
    pos?: { top: number; left: number; };
    title: string; desc: string; html: string;
  };
}


export const widgetCache = {

  set(key: string, content?: { top?: number; left?: number; status?: any }) {
    if (!this[key]) {
      this[key] = {top: 64, left: 64}
    }
    if (!content) return
    let field: keyof typeof content
    for (field in content) {
      this[key][field] = content[field]
    }
    localStorage.setItem(`Widget:${key}`, JSON.stringify(this[key]))
  },

  del(key: string) {
    delete this[key]
    localStorage.removeItem(`Widget:${key}`)
  }

} as WidgetCacheMap
