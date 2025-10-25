import logo from './logo.svg';
import './App.css';

import InformeDonaciones from "./components/informeDonaciones";

function App() {
  return (
    <div>
      <h1 style={{ textAlign: "center" }}>Gestión de Donaciones y Eventos</h1>
      <InformeDonaciones />
    </div>
  );
}

export default App;
