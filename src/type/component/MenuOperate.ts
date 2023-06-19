export type ClickOperate = (e: PointerEvent) => void

export interface MenuOperate {

  [key: string]: ClickOperate
}
