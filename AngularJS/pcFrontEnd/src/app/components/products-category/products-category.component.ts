import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Router} from '@angular/router';
import { ProductService } from '../../shared-service/product.service';
import { Product } from '../../product';

@Component({
  selector: 'app-products-category',
  templateUrl: './products-category.component.html',
  styleUrls: ['./products-category.component.css']
})
export class ProductsCategoryComponent implements OnInit {

  private category:String;
  private products:Product[];

  constructor( private route:ActivatedRoute, private router:Router, 
    private _productService:ProductService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.category = params['category'];
    });
    
    this._productService.getProductsOnCategory(this.category).subscribe((products)=>{
      this.products=products;
    },  (error)=>{
      console.log(error);
    }
  )

  }

  setClickedProduct(selectedRow:number){
    console.log("Clicked row "+ selectedRow);
    console.log("Clicked ProductId "+ this.products[selectedRow].productId);
    this._productService.setSelectedRow(this.products[selectedRow].productId);
    this.router.navigate(["/prddtls"]);
  }


}
