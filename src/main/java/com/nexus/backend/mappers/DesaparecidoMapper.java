//package com.nexus.backend.mappers;
//
//import com.nexus.backend.dto.desaparecido.DesaparecidoCriacaoDto;
//import com.nexus.backend.dto.desaparecido.DesaparecidoRespostaDto;
//import com.nexus.backend.entities.Desaparecido;
//import jakarta.validation.Valid;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DesaparecidoMapper {
//
//    public DesaparecidoRespostaDto toRespostaDto(Desaparecido entity){
//        if ( entity == null) return null;
//
//        return  DesaparecidoRespostaDto.builder()
//                .id(entity.getId())
//                .dataNascimento(entity.getDataNascimento())
//                .nome(entity.getNome())
//                .apelido(entity.getApelido())
//                .desapareceuAntes(entity.getDesapareceuAntes())
//                .idResponsavel(entity.getIdResponsavel())
//                .boletimOcorrencia(entity.getBoletimOcorrencia())
//                .dataOcorrencia(entity.getDataOcorrencia())
//                .imagem(entity.getImagem())
//                .cidade(entity.getCidade())
//                .sexo(entity.getSexo())
//                .ultimoLocalVisto(entity.getUltimoLocalVisto())
//                .corPele(entity.getCorPele())
//                .corOlhos(entity.getCorOlhos())
//                .roupaDesaparecimento(entity.getRoupaDesaparecimento())
//                .build();
//    }
//
//    public Desaparecido toCriacaoEntity (@Valid DesaparecidoCriacaoDto dto){
//        if (dto==null) return null;
//
//        return Desaparecido.builder()
//                .apelido(dto.getApelido())
//                .cpf(dto.getCpf())
//                .cep(dto.getCep())
//                .boletimOcorrencia(dto.getBoletimOcorrencia())
//                .cidade(dto.getCidade())
//                .corOlhos(dto.getCorOlhos())
//                .corPele(dto.getCorPele())
//                .dataComunicacao(dto.getDataComunicacao())
//                .rg(dto.getRg())
//                .dataNascimento(dto.getDataNascimento())
//                .dataOcorrencia(dto.getDataOcorrencia())
//                .desapareceuAntes(dto.getDesapareceuAntes())
//                .imagem(dto.getImagem())
//                .nomeMae(dto.getNomeMae())
//                .nomePai(dto.getNomePai())
//                .sexo(dto.getSexo())
//                .nome(dto.getNome())
//                .idResponsavel(dto.getIdResponsavel())
//                .estadoCivil(dto.getEstadoCivil())
//                .endereco(dto.getEndereco())
//                .telefone(dto.getTelefone())
//                .ultimoLocalVisto(dto.getUltimoLocalVisto())
//                .roupaDesaparecimento(dto.getRoupaDesaparecimento())
//                .build();
//    }
//
//}
