export const ItemTypes = {
  BOX: "box",
};

export interface DragItem {
  id: string;
  type: string;
  left: number;
  top: number;
}

export function snapToGrid(x: number, y: number): [number, number] {
  const snappedX = Math.round(x / 32) * 32;
  const snappedY = Math.round(y / 32) * 32;
  return [snappedX, snappedY];
}
