import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../shared-service/product.service';
import { Product } from '../../product';
import { Router } from '@angular/router';
import { Cart } from '../../cart';
import { CartService } from '../../shared-service/cart.service';
import { error } from 'util';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})

export class ProductDetailsComponent implements OnInit {
private product:Product = new Product();
private cart:Cart = new Cart();
private quantity:number;

  constructor(private _productService:ProductService, private _cartService:CartService,
     private _router:Router) { }

  ngOnInit() {
    if(this._productService.getSelectedRow() != undefined){
    this._productService.getProductOnID(this._productService.getSelectedRow())
    .subscribe((product)=>{
      this.product=product;
    },  (error)=>{
      console.log(error);
    }
  )
  }else{
    this._router.navigate(["/lst"]);}
  }

  addToCart(product:Product, quantity:Number){
    this.cart.productId = product.productId;
    this.cart.quantity = quantity;
    this._cartService.createCart(this.cart)
    .subscribe((cart)=>{
      this.cart = cart;
      this._cartService.setCart(cart);
      this._router.navigate(["/addtocart"]);
    }, (error)=>{
      console.log(error);
    }

    )
  }

}
