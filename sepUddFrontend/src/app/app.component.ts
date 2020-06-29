import { Component } from '@angular/core';
import {TestService} from './service/test.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'sepUddFrontend';

  constructor(private service: TestService) { }


    testService() {
    this.service.testt().subscribe(res => {
      console.log('success!');
    }, error1 => {
      console.log('error!');
    });
  }
}
