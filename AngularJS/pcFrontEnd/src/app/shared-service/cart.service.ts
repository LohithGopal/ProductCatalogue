import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { Product } from '../product';
import { Cart } from '../cart';

@Injectable()
export class CartService {
  private baseUrl:String='http://localhost:8080/pc';
  private header = new Headers({'content-type':'application/json'});
  private options= new RequestOptions({headers:this.header});
  private cart:Cart;

  constructor(private _http:Http) { }

  getCarts(){
    return this._http.get(this.baseUrl+"/cart", this.options)
    .map((response:Response)=> response.json())
    .catch(this.errorHandler);
  
  }

  createCart(cart:Cart){
    return this._http.post(this.baseUrl+"/cart", JSON.stringify(cart), this.options)
    .map((response:Response)=> response.json())
    .catch(this.errorHandler);
  }

  setCart(catalogue){
    this.cart = catalogue;
  }

  getCart(){
   return this.cart;
  }

  errorHandler(error:Response){
    return Observable.throw(error|| "Server Error !!!");
  }


}
