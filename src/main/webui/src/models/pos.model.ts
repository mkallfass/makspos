export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
}

export interface ProductSelection extends Product {
  quantity: number;
}

export interface LineItem extends ProductSelection {
  total: number;
}

export interface Order {
  lineitems: LineItem[];
  total: number;
}