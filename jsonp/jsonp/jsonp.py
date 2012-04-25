
"""Module docstring.

This serves as a long usage message.
"""
import sys
import getopt
import BaseHTTPServer
import urlparse
import urllib

class JsonpHTTPRequestHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_HEAD(self):
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()
    
    def getCallbackName(self,path):
        parsed_path = urlparse.urlparse(path)
        parameters = parsed_path[4].split('&')        
        for parameter in parameters:
            value = parameter.split('=')
            if len(value) > 1 and value[0] == 'callback':
                return value[1]
        return ""
    
    def buildNewURL(self,path):
        parsed_path = urlparse.urlparse(path)
        parameters = parsed_path[4].split('&')

            
        newUrl = "http://127.0.0.1:4444"
        newUrl = newUrl + parsed_path.path
        params = False
        
        for parameter in parameters:
            if parameter == "":
                continue  
            values = parameter.split('=')
            if not (len(values) == 2 and values[0] == 'callback'):
                if not params:
                    params = True
                    newUrl = newUrl+'?'
                else:
                    newUrl = newUrl+'&'                            
                newUrl = newUrl + parameter
            
        print('newUrl={}'.format(newUrl))
        return newUrl         
        
    
    def do_GET(self):
        """Respond to a GET request."""
        
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()
        
        f = urllib.urlopen(url=self.buildNewURL(self.path), proxies=None)
        callback = self.getCallbackName(self.path)
        print('callback name is {}'.format(callback))
        if callback == '':
            result = f.read()
        else:
            result = '{}({});'.format(callback,f.read())
        self.wfile.write(result)
        
def run_while_true(server_class=BaseHTTPServer.HTTPServer,
                   handler_class=BaseHTTPServer.BaseHTTPRequestHandler,
                   server_address=('',8001)):
    """
    This assumes that keep_running() is a function of no arguments which
    is tested initially and after each request.  If its return value
    is true, the server continues.
    """    
    print('Starting server on {}'.format(server_address))
    httpd = server_class(server_address, handler_class)
    while True:
        httpd.handle_request()

def main():
    print("starting the server")
    run_while_true(handler_class=JsonpHTTPRequestHandler)
    print("hello world!")
    
if __name__ == "__main__":
    main()