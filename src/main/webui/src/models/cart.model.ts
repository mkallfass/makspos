import type { ProductSelection } from "@/models/product.model";

export interface LineItem extends ProductSelection{
  total: number
}

export interface Cart {
  lineItems: Map<number, LineItem>
  total: number
}