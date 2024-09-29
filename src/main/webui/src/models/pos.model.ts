export interface BaseProduct {
  id: number
  name: string
  description: string
}

export interface Product extends BaseProduct {
  price: number
}

export interface ProductSelection extends Product {
  quantity: number
}

export interface LineItem extends ProductSelection {
  total: number
}

export interface Order {
  lineitems: LineItem[]
  total: number
}

export interface OrderStatistics {
  orderCount: number
  overallRevenue: number
  productStatistics: ProductStatistics[]
}

export interface ProductStatistics extends BaseProduct {
  orderedCount: number
  revenue: number
}
