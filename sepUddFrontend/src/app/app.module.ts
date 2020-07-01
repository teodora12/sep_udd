import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {NavbarComponent} from './component/navbar/navbar.component';
import {LoginPageComponent} from './component/login-page/login-page.component';
import {ToastrModule} from 'ng6-toastr-notifications';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {WorkDataPageComponent} from './component/work-data-page/work-data-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginPageComponent,
    WorkDataPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
