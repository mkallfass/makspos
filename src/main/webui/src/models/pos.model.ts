export interface BaseProduct {
  id: number;
  name: string;
  description: string;
}

export interface Product extends BaseProduct {
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

export interface OrderStatistic {
  orderCount: number;
  overallRevenue: number;
  productStatistics: ProductStatistic[];
}

export interface ProductStatistic extends BaseProduct {
  orderedCount: number;
  revenue: number;
}