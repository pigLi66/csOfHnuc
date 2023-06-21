export interface FixedToolCardMap {
  [key: string]: { top: number; left: number };
}

export interface FixedCustomCardMap {
  [key: string]: { top: number; left: number; html: string };
}

export interface ComponentCache<T> {
  [key: string]: T
}
