import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions, URLSearchParams } from '@angular/http';
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { Product } from '../product';



@Injectable()
export class ProductService {
  private baseUrl:String='http://localhost:8080/pc';
  private header = new Headers({'content-type':'application/json'});
  private options= new RequestOptions({headers:this.header});
  private product:Product;
  private searchString:String;
  private selectedRow:number;

  constructor(private _http:Http ) { }

  getProducts(){
    return this._http.get(this.baseUrl+"/products", this.options)
    .map((response:Response)=> response.json())
    .catch(this.errorHandler);
  }

  getProductsOnCategory(category:String){
    return this._http.get(this.baseUrl+"/products/"+category, this.options)
    .map((response:Response)=> response.json())
    .catch(this.errorHandler);
  }

  getProductsOnDesc(desc:String){
    return this._http.get(this.baseUrl+"/product/"+desc, this.options)
    .map((response:Response)=> response.json())
    .catch(this.errorHandler);
  }

  getProductOnID(id:number): Observable<Product>{
    let params:URLSearchParams = new URLSearchParams();
    params.set('productId', id.toString());
    this.options.search = params;

    return this._http.get(this.baseUrl+"/product", this.options)
    .map((response:Response)=> response.json() as Product)
    .catch(this.errorHandler);
  }


  createProduct(product:Product){
    return this._http.post(this.baseUrl+"/product", JSON.stringify(product), this.options)
    .map((response:Response)=> response.json())
    .catch(this.errorHandler);
  }

  updateProduct(product:Product){
    return this._http.put(this.baseUrl+"/product", JSON.stringify(product), this.options)
    .map((response:Response)=> response.json())
    .catch(this.errorHandler);
  }

  deleteProduct(id:Number){
    return this._http.delete(this.baseUrl+"/product/"+id, this.options)
    .map((response:Response)=> response.json())
    .catch(this.errorHandler);
  }
  
  setter(product:Product){
    this.product = product;
  }

  getter(){
    return this.product;
  }

  setSearchString(searchString:String){
    this.searchString=searchString;
  }

  getSearchString(){
    return this.searchString;
  }

  setSelectedRow(selectedRow:number){
    this.selectedRow = selectedRow;
  }

  getSelectedRow(){
    return this.selectedRow;
  }
  


  errorHandler(error:Response){
    return Observable.throw(error|| "Server Error !!!");
  }

}
