import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WorkService {

  constructor(private http: HttpClient) { }


  submitWorkData(workData): any {
    return this.http.post('api/works/submitWorkData', workData);
  }

  search(search, phrase, caregory): any {
    return this.http.get('api/search/');
  }

  getScientificFields(): any {
    return this.http.get('api/works/getScientificFields');
  }


}
