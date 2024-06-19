export interface IProduct {
  id?: number;
  title?: string | null;
  description?: string | null;
  price?: number | null;
}

export const defaultValue: Readonly<IProduct> = {};
