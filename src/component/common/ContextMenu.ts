import {createApp} from "vue";
import ContextMenu from "@/component/common/ContextMenu.vue";
import {MenuOperate} from "@/type/component/MenuOperate";

function create() {
  let div = document.createElement("div")
  div.id = '#context-menu'
  document.body.appendChild(div)

  return createApp(ContextMenu).mount(div)
}

let instance: any & {
  open(operate: MenuOperate, e: PointerEvent): void
  close(): void
} = create()

export default instance
