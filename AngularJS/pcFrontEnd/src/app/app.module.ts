import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'
import { HttpModule } from '@angular/http'
import { HttpClientModule } from '@angular/common/http/src/module';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { ListProductsComponent } from './components/list-products/list-products.component';

import { ProductService } from './shared-service/product.service';
import { CartService } from './shared-service/cart.service';
import { ProductsCategoryComponent } from './components/products-category/products-category.component';
import { AboutComponent } from './components/about/about.component';
import { ContactComponent } from './components/contact/contact.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { SearchProductsComponent } from './components/search-products/search-products.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { AddToCartComponent } from './components/add-to-cart/add-to-cart.component';


const appRoutes:Routes=[
  {pathMatch:'full', path:'lst', component:ListProductsComponent},
  {pathMatch:'full', path:'lst/:category', component:ProductsCategoryComponent},
  {pathMatch:'full', path:'add', component:AddProductComponent},
  {pathMatch:'full', path:'about', component:AboutComponent},
  {pathMatch:'full', path:'contact', component:ContactComponent},
  {pathMatch:'full', path:'search',  component:SearchProductsComponent},
  {pathMatch:'full', path:'prddtls',  component:ProductDetailsComponent},
  {pathMatch:'full', path:'addtocart',  component:AddToCartComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    ListProductsComponent,
    ProductsCategoryComponent,
    AboutComponent,
    ContactComponent,
    AddProductComponent,
    SearchProductsComponent,
    ProductDetailsComponent,
    AddToCartComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [ProductService, CartService],
  bootstrap: [AppComponent]
})
export class AppModule { }
