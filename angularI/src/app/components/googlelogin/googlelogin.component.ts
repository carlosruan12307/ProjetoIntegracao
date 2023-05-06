import { DOCUMENT } from '@angular/common';
import {
  Component,
  NgZone,
  OnInit,
  AfterViewInit,
  ViewChild,
  ElementRef,
  Inject,
  Renderer2,
} from '@angular/core';
import { Router } from '@angular/router';
import { CredentialResponse } from 'google-one-tap';
import { AuthService } from 'src/app/services/authService/auth-service.service';

@Component({
  selector: 'app-googlelogin',
  templateUrl: './googlelogin.component.html',
  styleUrls: ['./googlelogin.component.css'],
})
export class GoogleloginComponent implements AfterViewInit, OnInit {
  @ViewChild('buttonT') buttonT: ElementRef = new ElementRef({});
  constructor(
    private route: Router,
    private service: AuthService,
    private ngzone: NgZone,
    private _renderer2: Renderer2,
    @Inject(DOCUMENT) private _document: Document
  ) {}
  ngAfterViewInit(): void {
    const script1 = this._renderer2.createElement('script');
    script1.src = `https://accounts.google.com/gsi/client`;
    script1.async = `true`;
    script1.defer = `true`;
    this._renderer2.appendChild(this._document.body, script1);
  }

  mouse: string = 'notenter';
  teste: number = 0;
  tururu: any;
  left: number = 0;
  ngOnInit(): void {
    // @ts-ignore
    window.onGoogleLibraryLoad = () => {
      //@ts-ignore
      google.accounts.id.initialize({
        client_id:
          '336886880384-33mjig685qfsgvaivcbqkro60n6749k6.apps.googleusercontent.com',
        callback: this.handleResponse.bind(this),
        auto_select: false,
        cancel_on_tap_outside: true,
      });
      //@ts-ignore
      google.accounts.id.renderButton(this.buttonT.nativeElement, {
        type: 'standard',
        theme: 'outline',
        size: 'large',
        width: 100,
      });
      //@ts-ignore
      google.accounts.id.prompt((notification: PromptMomentNotification) => {});
    };
  }

  async handleResponse(response: CredentialResponse) {
    console.log('ola', response);
    await this.service.LoginWithGoogle(response.credential).subscribe({
      next: (userData) => {
        // localStorage.setItem('token', x.token);
        this.service.LoggedIn = true;
        this.service.userData = userData;
        this.ngzone.run(() => {
          this.route.navigate(['/home']);
        });
      },
      error: (error) => {
        console.log(response);

        this.ngzone.run(() => {
          this.route.navigate(['']);
        });
        console.log(error);
      },
    });
  }
  handleClick() {}
}
